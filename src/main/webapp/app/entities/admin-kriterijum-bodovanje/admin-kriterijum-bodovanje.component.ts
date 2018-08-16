import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';
import { Principal } from 'app/core';
import { AdminKriterijumBodovanjeService } from './admin-kriterijum-bodovanje.service';

@Component({
    selector: 'jhi-admin-kriterijum-bodovanje',
    templateUrl: './admin-kriterijum-bodovanje.component.html'
})
export class AdminKriterijumBodovanjeComponent implements OnInit, OnDestroy {
    adminKriterijumBodovanjes: IAdminKriterijumBodovanje[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private adminKriterijumBodovanjeService: AdminKriterijumBodovanjeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.adminKriterijumBodovanjeService.query().subscribe(
            (res: HttpResponse<IAdminKriterijumBodovanje[]>) => {
                this.adminKriterijumBodovanjes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAdminKriterijumBodovanjes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAdminKriterijumBodovanje) {
        return item.id;
    }

    registerChangeInAdminKriterijumBodovanjes() {
        this.eventSubscriber = this.eventManager.subscribe('adminKriterijumBodovanjeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
