import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProjekat } from 'app/shared/model/projekat.model';
import { Principal } from 'app/core';
import { ProjekatService } from './projekat.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'jhi-projekat',
    templateUrl: './projekat.component.html'
})
export class ProjekatComponent implements OnInit, OnDestroy {
    projekats: IProjekat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    params: any;
    id: any;

    constructor(
        private projekatService: ProjekatService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private route: ActivatedRoute
    ) {}

    // loadAll() {
    //     this.projekatService.query().subscribe(
    //         (res: HttpResponse<IProjekat[]>) => {
    //             this.projekats = res.body;
    //         },
    //         (res: HttpErrorResponse) => this.onError(res.message)
    //     );
    // }

    loadAll(id) {
        this.projekatService.queryByAkcioniPlan(id).subscribe(
            (res: HttpResponse<IProjekat[]>) => {
                this.projekats = res.body;
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
        this.registerChangeInProjekats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProjekat) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInProjekats() {
        this.eventSubscriber = this.eventManager.subscribe('projekatListModification', response => this.loadAll(this.id));
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
