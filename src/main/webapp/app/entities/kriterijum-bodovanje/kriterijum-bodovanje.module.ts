import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import {
    KriterijumBodovanjeComponent,
    KriterijumBodovanjeDetailComponent,
    KriterijumBodovanjeUpdateComponent,
    KriterijumBodovanjeDeletePopupComponent,
    KriterijumBodovanjeDeleteDialogComponent,
    kriterijumBodovanjeRoute,
    kriterijumBodovanjePopupRoute
} from './';

const ENTITY_STATES = [...kriterijumBodovanjeRoute, ...kriterijumBodovanjePopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        KriterijumBodovanjeComponent,
        KriterijumBodovanjeDetailComponent,
        KriterijumBodovanjeUpdateComponent,
        KriterijumBodovanjeDeleteDialogComponent,
        KriterijumBodovanjeDeletePopupComponent
    ],
    entryComponents: [
        KriterijumBodovanjeComponent,
        KriterijumBodovanjeUpdateComponent,
        KriterijumBodovanjeDeleteDialogComponent,
        KriterijumBodovanjeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanKriterijumBodovanjeModule {}
