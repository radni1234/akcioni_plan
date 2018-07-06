import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import { Principal } from 'app/core';
import { AkcioniPlanService } from './akcioni-plan.service';

@Component({
    selector: 'jhi-akcioni-plan',
    templateUrl: './akcioni-plan.component.html'
})
export class AkcioniPlanComponent implements OnInit, OnDestroy {
    akcioniPlans: IAkcioniPlan[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private akcioniPlanService: AkcioniPlanService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.akcioniPlanService.query().subscribe(
            (res: HttpResponse<IAkcioniPlan[]>) => {
                this.akcioniPlans = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAkcioniPlans();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAkcioniPlan) {
        return item.id;
    }

    registerChangeInAkcioniPlans() {
        this.eventSubscriber = this.eventManager.subscribe('akcioniPlanListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
