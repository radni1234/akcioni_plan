import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Projekat } from 'app/shared/model/projekat.model';
import { ProjekatService } from './projekat.service';
import { ProjekatComponent } from './projekat.component';
import { ProjekatDetailComponent } from './projekat-detail.component';
import { ProjekatUpdateComponent } from './projekat-update.component';
import { ProjekatDeletePopupComponent } from './projekat-delete-dialog.component';
import { IProjekat } from 'app/shared/model/projekat.model';
import {projekatBodovanjeRoute} from '../projekat-bodovanje/projekat-bodovanje.route';

@Injectable({ providedIn: 'root' })
export class ProjekatResolve implements Resolve<IProjekat> {
    constructor(private service: ProjekatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((projekat: HttpResponse<Projekat>) => projekat.body));
        }
        return of(new Projekat());
    }
}

export const projekatRoute: Routes = [
    {
        path: 'projekat',
        component: ProjekatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'projekat/new',
        component: ProjekatUpdateComponent,
        resolve: {
            projekat: ProjekatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'projekat/:id',
        children: [
            {
                path: 'view',
                component: ProjekatDetailComponent,
                resolve: {
                    projekat: ProjekatResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'akcioniPlanApp.projekat.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            {
                path: 'edit',
                component: ProjekatUpdateComponent,
                resolve: {
                    projekat: ProjekatResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'akcioniPlanApp.projekat.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            ...projekatBodovanjeRoute
        ]
    }
];

export const projekatPopupRoute: Routes = [
    {
        path: 'projekat/:id/delete',
        component: ProjekatDeletePopupComponent,
        resolve: {
            projekat: ProjekatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
