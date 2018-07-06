import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, Principal, Account } from 'app/core';
import {AkcioniPlanService} from '../entities/akcioni-plan/akcioni-plan.service';
import {IAkcioniPlan} from '../shared/model/akcioni-plan.model';
import {Subscription} from 'rxjs/Rx';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    akcioniPlans: IAkcioniPlan[];
    eventSubscriber: Subscription;
    prikaziDetalje = false;

    constructor(private akcioniPlanService: AkcioniPlanService,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private eventManager: JhiEventManager) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.loadAll();
        this.registerChangeInAkcioniPlans();
    }

    loadAll() {
        this.akcioniPlanService.query().subscribe(
            (res: HttpResponse<IAkcioniPlan[]>) => {
                this.akcioniPlans = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    registerChangeInAkcioniPlans() {
        this.eventSubscriber = this.eventManager.subscribe('akcioniPlanListModification', response => this.loadAll());
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    prikaz() {
        this.prikaziDetalje = !this.prikaziDetalje;
    }
}
