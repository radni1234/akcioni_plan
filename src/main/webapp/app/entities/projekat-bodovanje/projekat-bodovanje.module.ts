import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkcioniPlanSharedModule } from 'app/shared';
import {
    ProjekatBodovanjeComponent,
    ProjekatBodovanjeDetailComponent,
    ProjekatBodovanjeUpdateComponent,
    ProjekatBodovanjeDeletePopupComponent,
    ProjekatBodovanjeDeleteDialogComponent,
    projekatBodovanjeRoute,
    projekatBodovanjePopupRoute
} from './';

const ENTITY_STATES = [...projekatBodovanjeRoute, ...projekatBodovanjePopupRoute];

@NgModule({
    imports: [AkcioniPlanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProjekatBodovanjeComponent,
        ProjekatBodovanjeDetailComponent,
        ProjekatBodovanjeUpdateComponent,
        ProjekatBodovanjeDeleteDialogComponent,
        ProjekatBodovanjeDeletePopupComponent
    ],
    entryComponents: [
        ProjekatBodovanjeComponent,
        ProjekatBodovanjeUpdateComponent,
        ProjekatBodovanjeDeleteDialogComponent,
        ProjekatBodovanjeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanProjekatBodovanjeModule {}
