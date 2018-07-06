import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';
import { ProjekatBodovanjeService } from './projekat-bodovanje.service';
import { ProjekatBodovanjeComponent } from './projekat-bodovanje.component';
import { ProjekatBodovanjeDetailComponent } from './projekat-bodovanje-detail.component';
import { ProjekatBodovanjeUpdateComponent } from './projekat-bodovanje-update.component';
import { ProjekatBodovanjeDeletePopupComponent } from './projekat-bodovanje-delete-dialog.component';
import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';

@Injectable({ providedIn: 'root' })
export class ProjekatBodovanjeResolve implements Resolve<IProjekatBodovanje> {
    constructor(private service: ProjekatBodovanjeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((projekatBodovanje: HttpResponse<ProjekatBodovanje>) => projekatBodovanje.body));
        }
        return of(new ProjekatBodovanje());
    }
}

export const projekatBodovanjeRoute: Routes = [
    {
        path: 'projekat-bodovanje',
        component: ProjekatBodovanjeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekatBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'projekat-bodovanje/:id/view',
        component: ProjekatBodovanjeDetailComponent,
        resolve: {
            projekatBodovanje: ProjekatBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekatBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'projekat-bodovanje/new',
        component: ProjekatBodovanjeUpdateComponent,
        resolve: {
            projekatBodovanje: ProjekatBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekatBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'projekat-bodovanje/:id/edit',
        component: ProjekatBodovanjeUpdateComponent,
        resolve: {
            projekatBodovanje: ProjekatBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekatBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projekatBodovanjePopupRoute: Routes = [
    {
        path: 'projekat-bodovanje/:id/delete',
        component: ProjekatBodovanjeDeletePopupComponent,
        resolve: {
            projekatBodovanje: ProjekatBodovanjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'akcioniPlanApp.projekatBodovanje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
