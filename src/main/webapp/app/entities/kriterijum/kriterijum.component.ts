import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKriterijum } from 'app/shared/model/kriterijum.model';
import { Principal } from 'app/core';
import { KriterijumService } from './kriterijum.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'jhi-kriterijum',
    templateUrl: './kriterijum.component.html'
})
export class KriterijumComponent implements OnInit, OnDestroy {
    kriterijums: IKriterijum[];
    currentAccount: any;
    eventSubscriber: Subscription;

    params: any;
    id: any;

    constructor(
        private kriterijumService: KriterijumService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private route: ActivatedRoute
    ) {}

    // loadAll() {
    //     this.kriterijumService.query().subscribe(
    //         (res: HttpResponse<IKriterijum[]>) => {
    //             this.kriterijums = res.body;
    //         },
    //         (res: HttpErrorResponse) => this.onError(res.message)
    //     );
    // }

    loadAll(id) {
        this.kriterijumService.queryByAkcioniPlan(id).subscribe(
            (res: HttpResponse<IKriterijum[]>) => {
                this.kriterijums = res.body;
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

        this.loadAll(this.id);
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKriterijums();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IKriterijum) {
        return item.id;
    }

    registerChangeInKriterijums() {
        this.eventSubscriber = this.eventManager.subscribe('kriterijumListModification', response => this.loadAll(this.id));
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
