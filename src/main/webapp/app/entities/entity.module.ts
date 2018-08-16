import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AkcioniPlanAkcioniPlanModule } from './akcioni-plan/akcioni-plan.module';
import { AkcioniPlanProjekatModule } from './projekat/projekat.module';
import { AkcioniPlanKriterijumModule } from './kriterijum/kriterijum.module';
import { AkcioniPlanKriterijumBodovanjeModule } from './kriterijum-bodovanje/kriterijum-bodovanje.module';
import { AkcioniPlanProjekatBodovanjeModule } from './projekat-bodovanje/projekat-bodovanje.module';
import { AkcioniPlanAdminKriterijumModule } from './admin-kriterijum/admin-kriterijum.module';
import { AkcioniPlanAdminKriterijumBodovanjeModule } from './admin-kriterijum-bodovanje/admin-kriterijum-bodovanje.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AkcioniPlanAkcioniPlanModule,
        AkcioniPlanProjekatModule,
        AkcioniPlanKriterijumModule,
        AkcioniPlanKriterijumBodovanjeModule,
        AkcioniPlanProjekatBodovanjeModule,
        AkcioniPlanAdminKriterijumModule,
        AkcioniPlanAdminKriterijumBodovanjeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AkcioniPlanEntityModule {}
