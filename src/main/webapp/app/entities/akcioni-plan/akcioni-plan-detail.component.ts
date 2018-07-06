import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';

@Component({
    selector: 'jhi-akcioni-plan-detail',
    templateUrl: './akcioni-plan-detail.component.html'
})
export class AkcioniPlanDetailComponent implements OnInit {
    akcioniPlan: IAkcioniPlan;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ akcioniPlan }) => {
            this.akcioniPlan = akcioniPlan;
        });
    }

    previousState() {
        window.history.back();
    }
}
