import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import {
    KriterijumComponent,
    KriterijumDetailComponent,
    KriterijumUpdateComponent,
    KriterijumDeletePopupComponent,
    KriterijumDeleteDialogComponent,
    kriterijumRoute,
    kriterijumPopupRoute
} from './';

const ENTITY_STATES = [...kriterijumRoute, ...kriterijumPopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        KriterijumComponent,
        KriterijumDetailComponent,
        KriterijumUpdateComponent,
        KriterijumDeleteDialogComponent,
        KriterijumDeletePopupComponent
    ],
    entryComponents: [KriterijumComponent, KriterijumUpdateComponent, KriterijumDeleteDialogComponent, KriterijumDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanKriterijumModule {}
