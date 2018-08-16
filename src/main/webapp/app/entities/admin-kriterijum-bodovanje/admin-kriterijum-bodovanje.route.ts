import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';
import { AdminKriterijumBodovanjeService } from './admin-kriterijum-bodovanje.service';
import { AdminKriterijumBodovanjeComponent } from './admin-kriterijum-bodovanje.component';
import { AdminKriterijumBodovanjeDetailComponent } from './admin-kriterijum-bodovanje-detail.component';
import { AdminKriterijumBodovanjeUpdateComponent } from './admin-kriterijum-bodovanje-update.component';
import { AdminKriterijumBodovanjeDeletePopupComponent } from './admin-kriterijum-bodovanje-delete-dialog.component';
import { IAdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';

@Injectable({ providedIn: 'root' })
export class AdminKriterijumBodovanjeResolve implements Resolve<IAdminKriterijumBodovanje> {
    constructor(private service: AdminKriterijumBodovanjeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((adminKriterijumBodovanje: HttpResponse<AdminKriterijumBodovanje>) => adminKriterijumBodovanje.body));
        }
        return of(new AdminKriterijumBodovanje());
    }
}

export const adminKriterijumBodovanjeRoute: Routes = [
    {
        path: 'admin-kriterijum-bodovanje',
        component: AdminKriterijumBodovanjeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'admin-kriterijum-bodovanje/:id/view',
        component: AdminKriterijumBodovanjeDetailComponent,
        resolve: {
            adminKriterijumBodovanje: AdminKriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'admin-kriterijum-bodovanje/new',
        component: AdminKriterijumBodovanjeUpdateComponent,
        resolve: {
            adminKriterijumBodovanje: AdminKriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'admin-kriterijum-bodovanje/:id/edit',
        component: AdminKriterijumBodovanjeUpdateComponent,
        resolve: {
            adminKriterijumBodovanje: AdminKriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adminKriterijumBodovanjePopupRoute: Routes = [
    {
        path: 'admin-kriterijum-bodovanje/:id/delete',
        component: AdminKriterijumBodovanjeDeletePopupComponent,
        resolve: {
            adminKriterijumBodovanje: AdminKriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.adminKriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
