import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';
import { Principal } from 'app/core';
import { ProjekatBodovanjeService } from './projekat-bodovanje.service';
import {FormGroup, FormBuilder, FormArray, FormControl} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {KriterijumBodovanjeService} from '../kriterijum-bodovanje/kriterijum-bodovanje.service';
import {IKriterijumBodovanje} from '../../shared/model/kriterijum-bodovanje.model';
import {IKriterijum} from '../../shared/model/kriterijum.model';
import {Observable} from 'rxjs/Rx';

@Component({
    selector: 'jhi-projekat-bodovanje',
    templateUrl: './projekat-bodovanje.component.html',
    styleUrls: ['./projekat-bodovanje.component.css']
})
export class ProjekatBodovanjeComponent implements OnInit, OnDestroy {
    projekatBodovanjes: IProjekatBodovanje[];
    kriterijumBodovanje: IKriterijumBodovanje[];
    kriterijums: IKriterijum[];

    currentAccount: any;
    eventSubscriber: Subscription;

    rows: FormArray = this.fb.array([]);
    form: FormGroup = this.fb.group({ 'projekti': this.rows });

    bodovanje: any[] = [];

    projekatId: number;
    paramsProjekat: any;

    isSaving: boolean;

    constructor(
        private projekatBodovanjeService: ProjekatBodovanjeService,
        private kriterijumBodovanjeService: KriterijumBodovanjeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private fb: FormBuilder,
        private route: ActivatedRoute
    ) {}

    get projekti() {
        return this.form.get('projekti') as FormArray;
    }

    addRow(p: IProjekatBodovanje) {
        const row = this.fb.group({
            'kriterijumId'      : p.kriterijum.id,
            'kriterijum'        : p.kriterijum.naziv,
            'vrednost'          : p.vrednost,
            'tip'               : p.kriterijum.kriterijumTip,
            'bodovi'            : p.bodovi,
            'ponder'            : p.kriterijum.ponder,
            'ponderisaniBodovi' : p.ponderisaniBodovi
        });
        this.rows.push(row);
    }

    ngOnInit() {
        this.paramsProjekat = this.route.parent.params.subscribe(
            params => {
                console.warn(params);
                this.projekatId = params['id'];
            }
        );

        this.loadAll();

        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProjekatBodovanjes();
    }

    loadAll() {

        this.projekatBodovanjeService.queryByProjekat(this.projekatId).subscribe(
            (res: HttpResponse<IProjekatBodovanje[]>) => {
                this.projekatBodovanjes = res.body;

                this.projekatBodovanjes.forEach((p: IProjekatBodovanje) => {

                        this.kriterijumBodovanjeService.queryByKriterijum(p.kriterijum.id).subscribe(
                            (res2: HttpResponse<IKriterijumBodovanje[]>) => {
                                this.kriterijumBodovanje = res2.body;

                                p.kriterijum.kriterijumBodovanjes = this.kriterijumBodovanje;
                            }
                        );

                        this.addRow(p);
                    }
                );
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    izracunaj(x: FormControl) {

        let izracunitiBodovi;
        let izracunitiPonderisaniBodovi;

        const projekat = this.projekatBodovanjes.filter(p => p.kriterijum.id === x.value.kriterijumId);

        if (projekat[0].kriterijum.kriterijumBodovanjes) {
            if (projekat[0].kriterijum.kriterijumTip === 'BOD') {
                izracunitiBodovi = x.value.vrednost;
                izracunitiPonderisaniBodovi = izracunitiBodovi * x.value.ponder;
            } else if (projekat[0].kriterijum.kriterijumTip === 'VREDNOST') {
                const bodovanje = projekat[0].kriterijum.kriterijumBodovanjes.sort((n, m) => n.rb - m.rb);

                for (let j = 0; j < bodovanje.length; j++) {
                    if (x.value.vrednost) {
                        if (bodovanje[j].granica === null) {
                            izracunitiBodovi = bodovanje[j].bodovi;
                            izracunitiPonderisaniBodovi = izracunitiBodovi * x.value.ponder;
                        } else if (x.value.vrednost < bodovanje[j].granica) {
                            if (bodovanje[j].bodovi) {
                                izracunitiBodovi = bodovanje[j].bodovi;
                                izracunitiPonderisaniBodovi = izracunitiBodovi * x.value.ponder;
                                break;
                            }

                        }
                    }
                }
            }
        }

        x.get('bodovi').setValue(izracunitiBodovi);
        x.get('ponderisaniBodovi').setValue(izracunitiPonderisaniBodovi);

    }

    onSubmit() {
        // TODO: Use EventEmitter with form value
        console.warn(this.form.value);

        this.form.value.projekti.forEach(p => {
                const projekat = this.projekatBodovanjes.filter(pb => pb.kriterijum.id === p.kriterijumId);

                projekat[0].vrednost = p.vrednost;
                projekat[0].bodovi = p.bodovi;
                projekat[0].ponderisaniBodovi = p.ponderisaniBodovi;

                this.save(projekat[0]);
            }
        );
    }

    previousState() {
        window.history.back();
    }

    save(pb: IProjekatBodovanje) {
        this.isSaving = true;
        this.subscribeToSaveResponse(this.projekatBodovanjeService.update(pb));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProjekatBodovanje>>) {
        result.subscribe((res: HttpResponse<IProjekatBodovanje>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProjekatBodovanje) {
        return item.id;
    }

    registerChangeInProjekatBodovanjes() {
        this.eventSubscriber = this.eventManager.subscribe('projekatBodovanjeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
