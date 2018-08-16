import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAdminKriterijum } from 'app/shared/model/admin-kriterijum.model';
import { Principal } from 'app/core';
import { AdminKriterijumService } from './admin-kriterijum.service';

@Component({
    selector: 'jhi-admin-kriterijum',
    templateUrl: './admin-kriterijum.component.html'
})
export class AdminKriterijumComponent implements OnInit, OnDestroy {
    adminKriterijums: IAdminKriterijum[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private adminKriterijumService: AdminKriterijumService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.adminKriterijumService.query().subscribe(
            (res: HttpResponse<IAdminKriterijum[]>) => {
                this.adminKriterijums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAdminKriterijums();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAdminKriterijum) {
        return item.id;
    }

    registerChangeInAdminKriterijums() {
        this.eventSubscriber = this.eventManager.subscribe('adminKriterijumListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
