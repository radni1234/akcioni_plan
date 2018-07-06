import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';
import { Principal } from 'app/core';
import { ProjekatBodovanjeService } from './projekat-bodovanje.service';

@Component({
    selector: 'jhi-projekat-bodovanje',
    templateUrl: './projekat-bodovanje.component.html'
})
export class ProjekatBodovanjeComponent implements OnInit, OnDestroy {
    projekatBodovanjes: IProjekatBodovanje[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private projekatBodovanjeService: ProjekatBodovanjeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.projekatBodovanjeService.query().subscribe(
            (res: HttpResponse<IProjekatBodovanje[]>) => {
                this.projekatBodovanjes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProjekatBodovanjes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProjekatBodovanje) {
        return item.id;
    }

    registerChangeInProjekatBodovanjes() {
        this.eventSubscriber = this.eventManager.subscribe('projekatBodovanjeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
