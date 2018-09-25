import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';
import { Principal } from 'app/core';
import { KriterijumBodovanjeService } from './kriterijum-bodovanje.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'jhi-kriterijum-bodovanje',
    templateUrl: './kriterijum-bodovanje.component.html'
})
export class KriterijumBodovanjeComponent implements OnInit, OnDestroy {
    kriterijumBodovanjes: IKriterijumBodovanje[];
    currentAccount: any;
    eventSubscriber: Subscription;

    params: any;
    id: any;

    constructor(
        private kriterijumBodovanjeService: KriterijumBodovanjeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private route: ActivatedRoute
    ) {}

    // loadAll() {
    //     this.kriterijumBodovanjeService.query().subscribe(
    //         (res: HttpResponse<IKriterijumBodovanje[]>) => {
    //             this.kriterijumBodovanjes = res.body;
    //         },
    //         (res: HttpErrorResponse) => this.onError(res.message)
    //     );
    // }

    loadAll() {
        this.kriterijumBodovanjeService.queryByKriterijum(this.id).subscribe(
            (res: HttpResponse<IKriterijumBodovanje[]>) => {
                this.kriterijumBodovanjes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.params = this.route.parent.params.subscribe(
            params => {
                this.id = params['id'];
            }
        );

        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKriterijumBodovanjes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IKriterijumBodovanje) {
        return item.id;
    }

    registerChangeInKriterijumBodovanjes() {
        this.eventSubscriber = this.eventManager.subscribe('kriterijumBodovanjeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
