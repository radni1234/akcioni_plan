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
import {AkcioniPlanProjekatBodovanjeModule} from '../projekat-bodovanje/projekat-bodovanje.module';

const ENTITY_STATES = [...projekatRoute, ...projekatPopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES), AkcioniPlanProjekatBodovanjeModule],
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
