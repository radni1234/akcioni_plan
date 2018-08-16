import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdminKriterijum } from 'app/shared/model/admin-kriterijum.model';
import { AdminKriterijumService } from './admin-kriterijum.service';
import { AdminKriterijumComponent } from './admin-kriterijum.component';
import { AdminKriterijumDetailComponent } from './admin-kriterijum-detail.component';
import { AdminKriterijumUpdateComponent } from './admin-kriterijum-update.component';
import { AdminKriterijumDeletePopupComponent } from './admin-kriterijum-delete-dialog.component';
import { IAdminKriterijum } from 'app/shared/model/admin-kriterijum.model';

@Injectable({ providedIn: 'root' })
export class AdminKriterijumResolve implements Resolve<IAdminKriterijum> {
    constructor(private service: AdminKriterijumService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((adminKriterijum: HttpResponse<AdminKriterijum>) => adminKriterijum.body));
        }
        return of(new AdminKriterijum());
    }
}

export const adminKriterijumRoute: Routes = [
    {
        path: 'admin-kriterijum',
        component: AdminKriterijumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'admin-kriterijum/:id/view',
        component: AdminKriterijumDetailComponent,
        resolve: {
            adminKriterijum: AdminKriterijumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'admin-kriterijum/new',
        component: AdminKriterijumUpdateComponent,
        resolve: {
            adminKriterijum: AdminKriterijumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'admin-kriterijum/:id/edit',
        component: AdminKriterijumUpdateComponent,
        resolve: {
            adminKriterijum: AdminKriterijumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adminKriterijumPopupRoute: Routes = [
    {
        path: 'admin-kriterijum/:id/delete',
        component: AdminKriterijumDeletePopupComponent,
        resolve: {
            adminKriterijum: AdminKriterijumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
