import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IKriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';
import { KriterijumBodovanjeService } from './kriterijum-bodovanje.service';
import { IKriterijum } from 'app/shared/model/kriterijum.model';
import {KriterijumService} from '../kriterijum/kriterijum.service';
// import { KriterijumService } from 'app/entities/kriterijum';

@Component({
    selector: 'jhi-kriterijum-bodovanje-update',
    templateUrl: './kriterijum-bodovanje-update.component.html'
})
export class KriterijumBodovanjeUpdateComponent implements OnInit {
    private _kriterijumBodovanje: IKriterijumBodovanje;
    isSaving: boolean;

    kriterijums: IKriterijum[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private kriterijumBodovanjeService: KriterijumBodovanjeService,
        private kriterijumService: KriterijumService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ kriterijumBodovanje }) => {
            this.kriterijumBodovanje = kriterijumBodovanje;
        });
        this.kriterijumService.query().subscribe(
            (res: HttpResponse<IKriterijum[]>) => {
                this.kriterijums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.kriterijumBodovanje.id !== undefined) {
            this.subscribeToSaveResponse(this.kriterijumBodovanjeService.update(this.kriterijumBodovanje));
        } else {
            this.subscribeToSaveResponse(this.kriterijumBodovanjeService.create(this.kriterijumBodovanje));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKriterijumBodovanje>>) {
        result.subscribe((res: HttpResponse<IKriterijumBodovanje>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackKriterijumById(index: number, item: IKriterijum) {
        return item.id;
    }
    get kriterijumBodovanje() {
        return this._kriterijumBodovanje;
    }

    set kriterijumBodovanje(kriterijumBodovanje: IKriterijumBodovanje) {
        this._kriterijumBodovanje = kriterijumBodovanje;
    }
}
