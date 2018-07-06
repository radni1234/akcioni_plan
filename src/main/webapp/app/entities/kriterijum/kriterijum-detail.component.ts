import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKriterijum } from 'app/shared/model/kriterijum.model';

@Component({
    selector: 'jhi-kriterijum-detail',
    templateUrl: './kriterijum-detail.component.html'
})
export class KriterijumDetailComponent implements OnInit {
    kriterijum: IKriterijum;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kriterijum }) => {
            this.kriterijum = kriterijum;
        });
    }

    previousState() {
        window.history.back();
    }
}
