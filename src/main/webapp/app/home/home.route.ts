import { Route } from '@angular/router';

import { HomeComponent } from './';

import {akcioniPlanPopupRoute, akcioniPlanRoute} from '../entities/akcioni-plan/akcioni-plan.route';

const ENTITY_STATES = [...akcioniPlanRoute, ...akcioniPlanPopupRoute];

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    },
    children: ENTITY_STATES
}
;
