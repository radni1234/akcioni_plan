import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { KriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';
import { KriterijumBodovanjeService } from './kriterijum-bodovanje.service';
import { KriterijumBodovanjeComponent } from './kriterijum-bodovanje.component';
import { KriterijumBodovanjeDetailComponent } from './kriterijum-bodovanje-detail.component';
import { KriterijumBodovanjeUpdateComponent } from './kriterijum-bodovanje-update.component';
import { KriterijumBodovanjeDeletePopupComponent } from './kriterijum-bodovanje-delete-dialog.component';
import { IKriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';

@Injectable({ providedIn: 'root' })
export class KriterijumBodovanjeResolve implements Resolve<IKriterijumBodovanje> {
    constructor(private service: KriterijumBodovanjeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((kriterijumBodovanje: HttpResponse<KriterijumBodovanje>) => kriterijumBodovanje.body));
        }
        return of(new KriterijumBodovanje());
    }
}

export const kriterijumBodovanjeRoute: Routes = [
    {
        path: 'kriterijum-bodovanje',
        component: KriterijumBodovanjeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kriterijum-bodovanje/:id/view',
        component: KriterijumBodovanjeDetailComponent,
        resolve: {
            kriterijumBodovanje: KriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kriterijum-bodovanje/new',
        component: KriterijumBodovanjeUpdateComponent,
        resolve: {
            kriterijumBodovanje: KriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kriterijum-bodovanje/:id/edit',
        component: KriterijumBodovanjeUpdateComponent,
        resolve: {
            kriterijumBodovanje: KriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kriterijumBodovanjePopupRoute: Routes = [
    {
        path: 'kriterijum-bodovanje/:id/delete',
        component: KriterijumBodovanjeDeletePopupComponent,
        resolve: {
            kriterijumBodovanje: KriterijumBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.kriterijumBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
