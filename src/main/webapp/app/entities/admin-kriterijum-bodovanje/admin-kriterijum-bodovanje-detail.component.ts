import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';

@Component({
    selector: 'jhi-admin-kriterijum-bodovanje-detail',
    templateUrl: './admin-kriterijum-bodovanje-detail.component.html'
})
export class AdminKriterijumBodovanjeDetailComponent implements OnInit {
    adminKriterijumBodovanje: IAdminKriterijumBodovanje;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adminKriterijumBodovanje }) => {
            this.adminKriterijumBodovanje = adminKriterijumBodovanje;
        });
    }

    previousState() {
        window.history.back();
    }
}
