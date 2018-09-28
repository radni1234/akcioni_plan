import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Kriterijum } from 'app/shared/model/kriterijum.model';
import { KriterijumService } from './kriterijum.service';
import { KriterijumComponent } from './kriterijum.component';
import { KriterijumDetailComponent } from './kriterijum-detail.component';
import { KriterijumUpdateComponent } from './kriterijum-update.component';
import { KriterijumDeletePopupComponent } from './kriterijum-delete-dialog.component';
import { IKriterijum } from 'app/shared/model/kriterijum.model';
import {kriterijumBodovanjeRoute} from '../kriterijum-bodovanje/kriterijum-bodovanje.route';

@Injectable({ providedIn: 'root' })
export class KriterijumResolve implements Resolve<IKriterijum> {
    constructor(private service: KriterijumService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['kriterijum_id'] ? route.params['kriterijum_id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((kriterijum: HttpResponse<Kriterijum>) => kriterijum.body));
        }
        return of(new Kriterijum());
    }
}

export const kriterijumRoute: Routes = [
    {
        path: 'kriterijum',
        component: KriterijumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kriterijum/new',
        component: KriterijumUpdateComponent,
        resolve: {
            kriterijum: KriterijumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kriterijum/:kriterijum_id',
        children: [
            {
                path: 'view',
                component: KriterijumDetailComponent,
                resolve: {
                    kriterijum: KriterijumResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'akcioniPlanApp.kriterijum.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            {
                path: 'edit',
                component: KriterijumUpdateComponent,
                resolve: {
                    kriterijum: KriterijumResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'akcioniPlanApp.kriterijum.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            ...kriterijumBodovanjeRoute
        ]
    }
];

export const kriterijumPopupRoute: Routes = [
    {
        path: 'kriterijum/:kriterijum_id/delete',
        component: KriterijumDeletePopupComponent,
        resolve: {
            kriterijum: KriterijumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
