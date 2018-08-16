import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAdminKriterijum } from 'app/shared/model/admin-kriterijum.model';
import { AdminKriterijumService } from './admin-kriterijum.service';

@Component({
    selector: 'jhi-admin-kriterijum-update',
    templateUrl: './admin-kriterijum-update.component.html'
})
export class AdminKriterijumUpdateComponent implements OnInit {
    private _adminKriterijum: IAdminKriterijum;
    isSaving: boolean;

    constructor(private adminKriterijumService: AdminKriterijumService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adminKriterijum }) => {
            this.adminKriterijum = adminKriterijum;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adminKriterijum.id !== undefined) {
            this.subscribeToSaveResponse(this.adminKriterijumService.update(this.adminKriterijum));
        } else {
            this.subscribeToSaveResponse(this.adminKriterijumService.create(this.adminKriterijum));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdminKriterijum>>) {
        result.subscribe((res: HttpResponse<IAdminKriterijum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get adminKriterijum() {
        return this._adminKriterijum;
    }

    set adminKriterijum(adminKriterijum: IAdminKriterijum) {
        this._adminKriterijum = adminKriterijum;
    }
}
