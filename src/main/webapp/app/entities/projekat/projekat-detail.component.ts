import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProjekat } from 'app/shared/model/projekat.model';

@Component({
    selector: 'jhi-projekat-detail',
    templateUrl: './projekat-detail.component.html'
})
export class ProjekatDetailComponent implements OnInit {
    projekat: IProjekat;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projekat }) => {
            this.projekat = projekat;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
