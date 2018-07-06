import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';

@Component({
    selector: 'jhi-projekat-bodovanje-detail',
    templateUrl: './projekat-bodovanje-detail.component.html'
})
export class ProjekatBodovanjeDetailComponent implements OnInit {
    projekatBodovanje: IProjekatBodovanje;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projekatBodovanje }) => {
            this.projekatBodovanje = projekatBodovanje;
        });
    }

    previousState() {
        window.history.back();
    }
}
