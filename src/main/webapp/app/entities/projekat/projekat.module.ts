import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import {
    ProjekatComponent,
    ProjekatDetailComponent,
    ProjekatUpdateComponent,
    ProjekatDeletePopupComponent,
    ProjekatDeleteDialogComponent,
    projekatRoute,
    projekatPopupRoute
} from './';
import {ReactiveFormsModule} from '@angular/forms';

const ENTITY_STATES = [...projekatRoute, ...projekatPopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
    declarations: [
        ProjekatComponent,
        ProjekatDetailComponent,
        ProjekatUpdateComponent,
        ProjekatDeleteDialogComponent,
        ProjekatDeletePopupComponent
    ],
    entryComponents: [ProjekatComponent, ProjekatUpdateComponent, ProjekatDeleteDialogComponent, ProjekatDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanProjekatModule {}
