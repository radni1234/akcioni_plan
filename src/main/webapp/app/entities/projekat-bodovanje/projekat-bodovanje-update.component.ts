import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';
import { ProjekatBodovanjeService } from './projekat-bodovanje.service';
import { IKriterijum } from 'app/shared/model/kriterijum.model';
// import { KriterijumService } from 'app/entities/kriterijum';
import { IProjekat } from 'app/shared/model/projekat.model';
import {ProjekatService} from '../projekat/projekat.service';
import {KriterijumService} from '../kriterijum/kriterijum.service';
// import { ProjekatService } from 'app/entities/projekat';

@Component({
    selector: 'jhi-projekat-bodovanje-update',
    templateUrl: './projekat-bodovanje-update.component.html'
})
export class ProjekatBodovanjeUpdateComponent implements OnInit {
    private _projekatBodovanje: IProjekatBodovanje;
    isSaving: boolean;

    kriterijums: IKriterijum[];

    projekats: IProjekat[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private projekatBodovanjeService: ProjekatBodovanjeService,
        private kriterijumService: KriterijumService,
        private projekatService: ProjekatService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projekatBodovanje }) => {
            this.projekatBodovanje = projekatBodovanje;
        });
        this.kriterijumService.query().subscribe(
            (res: HttpResponse<IKriterijum[]>) => {
                this.kriterijums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.projekatService.query().subscribe(
            (res: HttpResponse<IProjekat[]>) => {
                this.projekats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.projekatBodovanje.id !== undefined) {
            this.subscribeToSaveResponse(this.projekatBodovanjeService.update(this.projekatBodovanje));
        } else {
            this.subscribeToSaveResponse(this.projekatBodovanjeService.create(this.projekatBodovanje));
        }
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackKriterijumById(index: number, item: IKriterijum) {
        return item.id;
    }

    trackProjekatById(index: number, item: IProjekat) {
        return item.id;
    }
    get projekatBodovanje() {
        return this._projekatBodovanje;
    }

    set projekatBodovanje(projekatBodovanje: IProjekatBodovanje) {
        this._projekatBodovanje = projekatBodovanje;
    }
}
