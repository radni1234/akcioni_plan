import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import { AkcioniPlanService } from './akcioni-plan.service';
import { AkcioniPlanComponent } from './akcioni-plan.component';
import { AkcioniPlanDetailComponent } from './akcioni-plan-detail.component';
import { AkcioniPlanUpdateComponent } from './akcioni-plan-update.component';
import { AkcioniPlanDeletePopupComponent } from './akcioni-plan-delete-dialog.component';
import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';

@Injectable({ providedIn: 'root' })
export class AkcioniPlanResolve implements Resolve<IAkcioniPlan> {
    constructor(private service: AkcioniPlanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((akcioniPlan: HttpResponse<AkcioniPlan>) => akcioniPlan.body));
        }
        return of(new AkcioniPlan());
    }
}

export const akcioniPlanRoute: Routes = [
    {
        path: 'akcioni-plan',
        component: AkcioniPlanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.akcioniPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'akcioni-plan/:id/view',
        component: AkcioniPlanDetailComponent,
        resolve: {
            akcioniPlan: AkcioniPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.akcioniPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'akcioni-plan/new',
        component: AkcioniPlanUpdateComponent,
        resolve: {
            akcioniPlan: AkcioniPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.akcioniPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'akcioni-plan/:id/edit',
        component: AkcioniPlanUpdateComponent,
        resolve: {
            akcioniPlan: AkcioniPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.akcioniPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const akcioniPlanPopupRoute: Routes = [
    {
        path: 'akcioni-plan/:id/delete',
        component: AkcioniPlanDeletePopupComponent,
        resolve: {
            akcioniPlan: AkcioniPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.akcioniPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
