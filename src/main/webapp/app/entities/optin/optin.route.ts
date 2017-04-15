import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OptinComponent } from './optin.component';
import { OptinDetailComponent } from './optin-detail.component';
import { OptinPopupComponent, OptinDialogComponent } from './optin-dialog.component';
import { OptinDeletePopupComponent } from './optin-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OptinResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const optinRoute: Routes = [
  {
    path: 'optin',
    component: OptinComponent,
    resolve: {
      'pagingParams': OptinResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.optin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'optin-new',
    component: OptinDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.optin.home.title'
    },
  }, {
    path: 'optin/:id',
    component: OptinDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.optin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const optinPopupRoute: Routes = [
  {
    path: 'optin/:id/edit',
    component: OptinPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.optin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'optin/:id/delete',
    component: OptinDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.optin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
