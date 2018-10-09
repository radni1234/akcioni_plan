import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IProjekat } from 'app/shared/model/projekat.model';
import { ProjekatService } from './projekat.service';
import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import {AkcioniPlanService} from '../akcioni-plan/akcioni-plan.service';
// import { AkcioniPlanService } from 'app/entities/akcioni-plan';

@Component({
    selector: 'jhi-projekat-update',
    templateUrl: './projekat-update.component.html'
})
export class ProjekatUpdateComponent implements OnInit {
    private _projekat: IProjekat;
    isSaving: boolean;

    // akcioniplans: IAkcioniPlan[];
    akcioniPlan: IAkcioniPlan;
    params: any;
    datumOdDp: any;
    datumDoDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private projekatService: ProjekatService,
        private akcioniPlanService: AkcioniPlanService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projekat }) => {
            this.projekat = projekat;
        });
        // this.akcioniPlanService.query().subscribe(
        //     (res: HttpResponse<IAkcioniPlan[]>) => {
        //         this.akcioniplans = res.body;
        //     },
        //     (res: HttpErrorResponse) => this.onError(res.message)
        // );

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
            this.subscribeToSaveResponse(this.projekatService.update(this.projekat));
        } else {
            this.projekat.akcioniPlan = this.akcioniPlan;
            this.subscribeToSaveResponse(this.projekatService.create(this.projekat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProjekat>>) {
        result.subscribe((res: HttpResponse<IProjekat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
