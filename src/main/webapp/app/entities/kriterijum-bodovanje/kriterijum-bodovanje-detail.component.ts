import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';

@Component({
    selector: 'jhi-kriterijum-bodovanje-detail',
    templateUrl: './kriterijum-bodovanje-detail.component.html'
})
export class KriterijumBodovanjeDetailComponent implements OnInit {
    kriterijumBodovanje: IKriterijumBodovanje;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kriterijumBodovanje }) => {
            this.kriterijumBodovanje = kriterijumBodovanje;
        });
    }

    previousState() {
        window.history.back();
    }
}
