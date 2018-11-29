import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import {IKriterijum, KriterijumTip} from 'app/shared/model/kriterijum.model';
import { KriterijumService } from './kriterijum.service';
import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import {AkcioniPlanService} from '../akcioni-plan/akcioni-plan.service';
import {IKriterijumBodovanje, KriterijumBodovanje} from '../../shared/model/kriterijum-bodovanje.model';
import {KriterijumBodovanjeService} from '../kriterijum-bodovanje/kriterijum-bodovanje.service';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
// import { AkcioniPlanService } from 'app/entities/akcioni-plan';

@Component({
    selector: 'jhi-kriterijum-update',
    templateUrl: './kriterijum-update.component.html',
    styleUrls: ['./kriterijum-update.component.css']
})
export class KriterijumUpdateComponent implements OnInit {
    private _kriterijum: IKriterijum;

    isSaving: boolean;

    akcioniPlan: IAkcioniPlan;
    kriterijumBodovanjes: IKriterijumBodovanje[];
    params: any;

    rows: FormArray = this.fb.array([]);
    form: FormGroup = this.fb.group({ 'bodovanje': this.rows });

    kb: any;

    get bodovanje() {
        return this.form.get('bodovanje') as FormArray;
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private kriterijumService: KriterijumService,
        private kriterijumBodovanjeService: KriterijumBodovanjeService,
        private akcioniPlanService: AkcioniPlanService,
        private activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ kriterijum }) => {
            this.kriterijum = kriterijum;
        });

        if (this.kriterijum.id === undefined) {
            this.params = this.activatedRoute.parent.params.subscribe(
                params => {
                    console.log(params);
                    this.akcioniPlanService.find(params['id']).subscribe(
                        (res: HttpResponse<IAkcioniPlan>) => {
                            this.akcioniPlan = res.body;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                }
            );

            for (let i = 1; i < 5; i++) {
                this.addRowBodovanjeNew(i);
            }

            this.kriterijum.kriterijumTip = KriterijumTip.VREDNOST;

            this.popuniGranicaOd();

        } else {
            this.kriterijumBodovanjeService.queryByKriterijum(this.kriterijum.id).subscribe(
                (res: HttpResponse<IKriterijumBodovanje[]>) => {
                    this.kriterijumBodovanjes = res.body.sort((n, m) => n.rb - m.rb);

                    this.kriterijumBodovanjes.forEach((k: IKriterijumBodovanje) => {
                        this.addRowBodovanje(k);
                    });

                    this.popuniGranicaOd();
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    addRowBodovanje(kb: IKriterijumBodovanje) {
        const row = this.fb.group({
            'id': kb.id,
            'rb': kb.rb,
            'granicaOd': kb.granica,
            'granicaDo': kb.granica,
            'opis': kb.opis,
            'bodovi': kb.bodovi,
            'kriterijum': kb.kriterijum
        });
        this.rows.push(row);
        // console.warn(this.form);
    }

    addRowBodovanjeNew(x: number) {
        const row = this.fb.group({
            'rb': x,
            'granicaOd': 0,
            'granicaDo': 0,
            'opis': null,
            'bodovi': x
        });
        this.rows.push(row);
    }

    popuniGranicaOd() {
        let granicaDo1;
        let granicaDo2;
        let granicaDo3;

        this.bodovanje.controls.forEach((k: FormControl) => {
            console.warn(k);
            if (k.value.rb === 1) {
                granicaDo1 = k.value.granicaDo;
                k.get('granicaOd').setValue('Od 0 do ');
            } else if (k.value.rb === 2) {
                granicaDo2 = k.value.granicaDo;
                k.get('granicaOd').setValue('Od ' + granicaDo1 + ' do ');
            } else if (k.value.rb === 3) {
                granicaDo3 = k.value.granicaDo;
                k.get('granicaOd').setValue('Od ' + granicaDo2 + ' do ');
            } else if (k.value.rb === 4) {
                k.get('granicaOd').setValue('Preko ' + granicaDo3);
            }
        });
    }

    promeniBodovanje() {
        this.bodovanje.controls.forEach((k: FormControl) => {
            if (k.value.bodovi === 1) {
                k.get('bodovi').setValue(4);
            } else if (k.value.bodovi === 2) {
                k.get('bodovi').setValue(3);
            } else if (k.value.bodovi === 3) {
                k.get('bodovi').setValue(2);
            } else if (k.value.bodovi === 4) {
                k.get('bodovi').setValue(1);
            }
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.kriterijum.id !== undefined) {
            this.form.value.bodovanje.forEach(b => {
                    const kriterijumBodovanje = this.kriterijumBodovanjes.filter(kb => kb.id === b.id);

                    if (b.rb === 4) {
                        kriterijumBodovanje[0].granica = null;
                    } else {
                        kriterijumBodovanje[0].granica = b.granicaDo;
                    }

                    kriterijumBodovanje[0].bodovi = b.bodovi;
                    kriterijumBodovanje[0].opis = b.opis;

                    this.subscribeToSaveResponseBodovanje(this.kriterijumBodovanjeService.update(kriterijumBodovanje[0]));
                }
            );

            this.subscribeToSaveResponse(this.kriterijumService.update(this.kriterijum));
        } else {
            this.kriterijum.akcioniPlan = this.akcioniPlan;

            this.kriterijumService.create(this.kriterijum)
                .subscribe((res: HttpResponse<IKriterijum>) => {
                        this.form.value.bodovanje.forEach(b => {
                            const kb = new KriterijumBodovanje();
                            kb.kriterijum = res.body;
                            kb.kriterijum.kriterijumBodovanjes = null;
                            kb.kriterijum.projekatBodovanjes = null;
                            kb.rb = b.rb;
                            kb.granica = b.granicaDo;
                            kb.opis = b.opis;
                            kb.bodovi = b.bodovi;

                            console.warn(kb);
                            this.kb = kb;
                            this.kriterijumBodovanjeService.create(kb)
                                .subscribe((res2: HttpResponse<IKriterijumBodovanje>) => null, (res2: HttpErrorResponse) => this.onSaveError());
                        });

                        this.onSaveSuccess();
                    }
                    , (res: HttpErrorResponse) => this.onSaveError());
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKriterijum>>) {
        result.subscribe((res: HttpResponse<IKriterijum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponseBodovanje(result: Observable<HttpResponse<IKriterijumBodovanje>>) {
        result.subscribe((res: HttpResponse<IKriterijumBodovanje>) => null, (res: HttpErrorResponse) => this.onSaveError());
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

    get kriterijum() {
        return this._kriterijum;
    }

    set kriterijum(kriterijum: IKriterijum) {
        this._kriterijum = kriterijum;
    }
}
