import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';
import { AdminKriterijumBodovanjeService } from './admin-kriterijum-bodovanje.service';
import { IAdminKriterijum } from 'app/shared/model/admin-kriterijum.model';
import { AdminKriterijumService } from 'app/entities/admin-kriterijum';

@Component({
    selector: 'jhi-admin-kriterijum-bodovanje-update',
    templateUrl: './admin-kriterijum-bodovanje-update.component.html'
})
export class AdminKriterijumBodovanjeUpdateComponent implements OnInit {
    private _adminKriterijumBodovanje: IAdminKriterijumBodovanje;
    isSaving: boolean;

    adminkriterijums: IAdminKriterijum[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adminKriterijumBodovanjeService: AdminKriterijumBodovanjeService,
        private adminKriterijumService: AdminKriterijumService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adminKriterijumBodovanje }) => {
            this.adminKriterijumBodovanje = adminKriterijumBodovanje;
        });
        this.adminKriterijumService.query().subscribe(
            (res: HttpResponse<IAdminKriterijum[]>) => {
                this.adminkriterijums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adminKriterijumBodovanje.id !== undefined) {
            this.subscribeToSaveResponse(this.adminKriterijumBodovanjeService.update(this.adminKriterijumBodovanje));
        } else {
            this.subscribeToSaveResponse(this.adminKriterijumBodovanjeService.create(this.adminKriterijumBodovanje));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdminKriterijumBodovanje>>) {
        result.subscribe(
            (res: HttpResponse<IAdminKriterijumBodovanje>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackAdminKriterijumById(index: number, item: IAdminKriterijum) {
        return item.id;
    }
    get adminKriterijumBodovanje() {
        return this._adminKriterijumBodovanje;
    }

    set adminKriterijumBodovanje(adminKriterijumBodovanje: IAdminKriterijumBodovanje) {
        this._adminKriterijumBodovanje = adminKriterijumBodovanje;
    }
}
