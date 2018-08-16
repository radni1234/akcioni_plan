import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import {
    AdminKriterijumBodovanjeComponent,
    AdminKriterijumBodovanjeDetailComponent,
    AdminKriterijumBodovanjeUpdateComponent,
    AdminKriterijumBodovanjeDeletePopupComponent,
    AdminKriterijumBodovanjeDeleteDialogComponent,
    adminKriterijumBodovanjeRoute,
    adminKriterijumBodovanjePopupRoute
} from './';

const ENTITY_STATES = [...adminKriterijumBodovanjeRoute, ...adminKriterijumBodovanjePopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdminKriterijumBodovanjeComponent,
        AdminKriterijumBodovanjeDetailComponent,
        AdminKriterijumBodovanjeUpdateComponent,
        AdminKriterijumBodovanjeDeleteDialogComponent,
        AdminKriterijumBodovanjeDeletePopupComponent
    ],
    entryComponents: [
        AdminKriterijumBodovanjeComponent,
        AdminKriterijumBodovanjeUpdateComponent,
        AdminKriterijumBodovanjeDeleteDialogComponent,
        AdminKriterijumBodovanjeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanAdminKriterijumBodovanjeModule {}
