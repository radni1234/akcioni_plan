import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminKriterijum } from 'app/shared/model/admin-kriterijum.model';

@Component({
    selector: 'jhi-admin-kriterijum-detail',
    templateUrl: './admin-kriterijum-detail.component.html'
})
export class AdminKriterijumDetailComponent implements OnInit {
    adminKriterijum: IAdminKriterijum;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adminKriterijum }) => {
            this.adminKriterijum = adminKriterijum;
        });
    }

    previousState() {
        window.history.back();
    }
}
