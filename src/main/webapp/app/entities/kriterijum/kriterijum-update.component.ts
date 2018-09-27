import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IKriterijum } from 'app/shared/model/kriterijum.model';
import { KriterijumService } from './kriterijum.service';
import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import {AkcioniPlanService} from '../akcioni-plan/akcioni-plan.service';
// import { AkcioniPlanService } from 'app/entities/akcioni-plan';

@Component({
    selector: 'jhi-kriterijum-update',
    templateUrl: './kriterijum-update.component.html'
})
export class KriterijumUpdateComponent implements OnInit {
    private _kriterijum: IKriterijum;

    isSaving: boolean;

    // akcioniplans: IAkcioniPlan[];
    akcioniPlan: IAkcioniPlan;
    params: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private kriterijumService: KriterijumService,
        private akcioniPlanService: AkcioniPlanService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ kriterijum }) => {
            this.kriterijum = kriterijum;
        });

        // this.akcioniPlanService.query().subscribe(
        //     (res: HttpResponse<IAkcioniPlan[]>) => {
        //         this.akcioniplans = res.body;
        //     },
        //     (res: HttpErrorResponse) => this.onError(res.message)
        // );

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
        }
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.kriterijum.id !== undefined) {
            this.subscribeToSaveResponse(this.kriterijumService.update(this.kriterijum));
        } else {
            this.kriterijum.akcioniPlan = this.akcioniPlan;
            this.subscribeToSaveResponse(this.kriterijumService.create(this.kriterijum));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKriterijum>>) {
        result.subscribe((res: HttpResponse<IKriterijum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
