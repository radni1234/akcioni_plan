import {Component, OnInit, ElementRef, OnDestroy} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {JhiAlertService, JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import { IProjekat } from 'app/shared/model/projekat.model';
import { ProjekatService } from './projekat.service';
import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import {AkcioniPlanService} from '../akcioni-plan/akcioni-plan.service';
import {ProjekatBodovanjeService} from '../projekat-bodovanje/projekat-bodovanje.service';
import {KriterijumBodovanjeService} from '../kriterijum-bodovanje/kriterijum-bodovanje.service';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {IProjekatBodovanje, ProjekatBodovanje} from '../../shared/model/projekat-bodovanje.model';
import {IKriterijumBodovanje} from '../../shared/model/kriterijum-bodovanje.model';
import {Subscription} from 'rxjs/Rx';
import {KriterijumService} from '../kriterijum/kriterijum.service';
import {IKriterijum} from '../../shared/model/kriterijum.model';

@Component({
    selector: 'jhi-projekat-update',
    templateUrl: './projekat-update.component.html',
    styleUrls: ['./projekat-update.component.css']
})
export class ProjekatUpdateComponent implements OnInit, OnDestroy {

    private _projekat: IProjekat;
    isSaving: boolean;

    akcioniPlan: IAkcioniPlan;
    params: any;
    datumOdDp: any;
    datumDoDp: any;

    eventSubscriber: Subscription;

    projekatBodovanjes: IProjekatBodovanje[];
    kriterijumBodovanje: IKriterijumBodovanje[];
    kriterijums: IKriterijum[];

    rows: FormArray = this.fb.array([]);
    form: FormGroup = this.fb.group({ 'projekti': this.rows });

    get projekti() {
        return this.form.get('projekti') as FormArray;
    }

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private projekatService: ProjekatService,
        private akcioniPlanService: AkcioniPlanService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute,
        private projekatBodovanjeService: ProjekatBodovanjeService,
        private kriterijumService: KriterijumService,
        private kriterijumBodovanjeService: KriterijumBodovanjeService,
        private eventManager: JhiEventManager,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;

        // projekat
        this.activatedRoute.data.subscribe(({ projekat }) => {
            this.projekat = projekat;
        });

        if (this.projekat.id === undefined) {
            this.params = this.activatedRoute.parent.params.subscribe(
                params => {
                    this.akcioniPlanService.find(params['id']).subscribe(
                        (res: HttpResponse<IAkcioniPlan>) => {
                            this.akcioniPlan = res.body;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                }
            );
        }

        // projekat bodovanje
        this.loadAll();

        this.registerChangeInProjekatBodovanjes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    loadAll() {
        if (this.projekat.id !== undefined) {
            this.projekatBodovanjeService.queryByProjekat(this.projekat.id).subscribe(
                (res: HttpResponse<IProjekatBodovanje[]>) => {
                    this.projekatBodovanjes = res.body;

                    this.projekatBodovanjes.forEach((p: IProjekatBodovanje) => {

                            this.kriterijumBodovanjeService.queryByKriterijum(p.kriterijum.id).subscribe(
                                (res2: HttpResponse<IKriterijumBodovanje[]>) => {
                                    this.kriterijumBodovanje = res2.body;

                                    p.kriterijum.kriterijumBodovanjes = this.kriterijumBodovanje;
                                }
                            );

                            this.addRowProjekatBodovanje(p);
                        }
                    );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        } else {
            this.params = this.activatedRoute.parent.params.subscribe(
                params => {
                    this.kriterijumService.queryByAkcioniPlan(params['id']).subscribe(
                        (res: HttpResponse<IKriterijum[]>) => {
                            this.kriterijums = res.body;

                            this.kriterijums.forEach((k: IKriterijum) => {

                                    this.kriterijumBodovanjeService.queryByKriterijum(k.id).subscribe(
                                        (res2: HttpResponse<IKriterijumBodovanje[]>) => {
                                            this.kriterijumBodovanje = res2.body;

                                            k.kriterijumBodovanjes = this.kriterijumBodovanje;
                                        }
                                    );

                                    this.addRowKriterijum(k);
                                }
                            );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                }
            );
        }
    }

    addRowKriterijum(k: IKriterijum) {
        const row = this.fb.group({
            'kriterijumId'      : k.id,
            'kriterijum'        : k.naziv,
            'vrednost'          : null,
            'tip'               : k.kriterijumTip,
            'bodovi'            : null,
            'ponder'            : k.ponder,
            'ponderisaniBodovi' : null
        });
        this.rows.push(row);
    }

    addRowProjekatBodovanje(p: IProjekatBodovanje) {
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

    registerChangeInProjekatBodovanjes() {
        this.eventSubscriber = this.eventManager.subscribe('projekatBodovanjeListModification', response => this.loadAll());
    }

    izracunaj(x: FormControl) {

        let izracunitiBodovi;
        let izracunitiPonderisaniBodovi;
        let kriterijum;

        if (this.projekat.id !== undefined) {
            kriterijum = this.projekatBodovanjes.filter(p => p.kriterijum.id === x.value.kriterijumId)[0].kriterijum;
        }  else {
            kriterijum = this.kriterijums.filter(k => k.id === x.value.kriterijumId)[0];
        }

        if (kriterijum.kriterijumBodovanjes) {
            if (kriterijum.kriterijumTip === 'BOD') {
                izracunitiBodovi = x.value.vrednost;
                izracunitiPonderisaniBodovi = izracunitiBodovi * x.value.ponder;
            } else if (kriterijum.kriterijumTip === 'VREDNOST') {
                const bodovanje = kriterijum.kriterijumBodovanjes.sort((n, m) => n.rb - m.rb);

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

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.projekat, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;

        if (this.projekat.id !== undefined) {
            this.form.value.projekti.forEach(p => {
                    const projekatBodovanje = this.projekatBodovanjes.filter(pb => pb.kriterijum.id === p.kriterijumId);

                    projekatBodovanje[0].vrednost = p.vrednost;
                    projekatBodovanje[0].bodovi = p.bodovi;
                    projekatBodovanje[0].ponderisaniBodovi = p.ponderisaniBodovi;

                    this.subscribeToSaveResponseBodovanje(this.projekatBodovanjeService.update(projekatBodovanje[0]));
                }
            );

            this.subscribeToSaveResponse(this.projekatService.update(this.projekat));

        } else {
            this.projekat.akcioniPlan = this.akcioniPlan;

            this.projekatService.create(this.projekat)
                .subscribe((res: HttpResponse<IProjekat>) => {
                        this.form.value.projekti.forEach(p => {
                            const kriterijum = this.kriterijums.filter(k => k.id === p.kriterijumId)[0];

                            const pb = new ProjekatBodovanje();
                            pb.projekat = res.body;
                            pb.kriterijum = kriterijum;
                            pb.vrednost = p.vrednost;
                            pb.bodovi = p.bodovi;
                            pb.ponderisaniBodovi = p.ponderisaniBodovi;

                            this.projekatBodovanjeService.create(pb)
                                .subscribe((res2: HttpResponse<IProjekatBodovanje>) => null, (res2: HttpErrorResponse) => this.onSaveError());
                        });

                        this.onSaveSuccess();
                    }
                    , (res: HttpErrorResponse) => this.onSaveError());
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProjekat>>) {
        result.subscribe((res: HttpResponse<IProjekat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponseBodovanje(result: Observable<HttpResponse<IProjekatBodovanje>>) {
        result.subscribe((res: HttpResponse<IProjekatBodovanje>) => null, (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAkcioniPlanById(index: number, item: IAkcioniPlan) {
        return item.id;
    }

    get projekat() {
        return this._projekat;
    }

    set projekat(projekat: IProjekat) {
        this._projekat = projekat;
    }
}
