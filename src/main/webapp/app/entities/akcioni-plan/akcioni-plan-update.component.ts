import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import { AkcioniPlanService } from './akcioni-plan.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-akcioni-plan-update',
    templateUrl: './akcioni-plan-update.component.html'
})
export class AkcioniPlanUpdateComponent implements OnInit {
    private _akcioniPlan: IAkcioniPlan;
    isSaving: boolean;

    users: IUser[];
    datumOdDp: any;
    datumDoDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private akcioniPlanService: AkcioniPlanService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ akcioniPlan }) => {
            this.akcioniPlan = akcioniPlan;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.akcioniPlan.id !== undefined) {
            this.subscribeToSaveResponse(this.akcioniPlanService.update(this.akcioniPlan));
        } else {
            this.subscribeToSaveResponse(this.akcioniPlanService.create(this.akcioniPlan));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAkcioniPlan>>) {
        result.subscribe((res: HttpResponse<IAkcioniPlan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get akcioniPlan() {
        return this._akcioniPlan;
    }

    set akcioniPlan(akcioniPlan: IAkcioniPlan) {
        this._akcioniPlan = akcioniPlan;
    }
}
