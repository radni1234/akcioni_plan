import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import { AkcioniPlanAdminModule } from 'app/admin/admin.module';
import {
    AkcioniPlanComponent,
    AkcioniPlanDetailComponent,
    AkcioniPlanUpdateComponent,
    AkcioniPlanDeletePopupComponent,
    AkcioniPlanDeleteDialogComponent,
    akcioniPlanRoute,
    akcioniPlanPopupRoute
} from './';
import {AkcioniPlanTabComponent} from './akcioni-plan-tab.component';

const ENTITY_STATES = [...akcioniPlanRoute, ...akcioniPlanPopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, AkcioniPlanAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AkcioniPlanComponent,
        AkcioniPlanDetailComponent,
        AkcioniPlanUpdateComponent,
        AkcioniPlanDeleteDialogComponent,
        AkcioniPlanDeletePopupComponent,
        AkcioniPlanTabComponent
    ],
    entryComponents: [AkcioniPlanComponent, AkcioniPlanUpdateComponent, AkcioniPlanDeleteDialogComponent, AkcioniPlanDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanAkcioniPlanModule {}
