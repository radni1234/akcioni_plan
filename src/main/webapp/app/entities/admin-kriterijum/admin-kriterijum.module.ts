import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import {
    AdminKriterijumComponent,
    AdminKriterijumDetailComponent,
    AdminKriterijumUpdateComponent,
    AdminKriterijumDeletePopupComponent,
    AdminKriterijumDeleteDialogComponent,
    adminKriterijumRoute,
    adminKriterijumPopupRoute
} from './';

const ENTITY_STATES = [...adminKriterijumRoute, ...adminKriterijumPopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdminKriterijumComponent,
        AdminKriterijumDetailComponent,
        AdminKriterijumUpdateComponent,
        AdminKriterijumDeleteDialogComponent,
        AdminKriterijumDeletePopupComponent
    ],
    entryComponents: [
        AdminKriterijumComponent,
        AdminKriterijumUpdateComponent,
        AdminKriterijumDeleteDialogComponent,
        AdminKriterijumDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanAdminKriterijumModule {}
