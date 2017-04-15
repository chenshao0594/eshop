import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerOptinComponent } from './customer-optin.component';
import { CustomerOptinDetailComponent } from './customer-optin-detail.component';
import { CustomerOptinPopupComponent, CustomerOptinDialogComponent } from './customer-optin-dialog.component';
import { CustomerOptinDeletePopupComponent } from './customer-optin-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerOptinResolvePagingParams implements Resolve<any> {

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

export const customerOptinRoute: Routes = [
  {
    path: 'customer-optin',
    component: CustomerOptinComponent,
    resolve: {
      'pagingParams': CustomerOptinResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-optin-new',
    component: CustomerOptinDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptin.home.title'
    },
  }, {
    path: 'customer-optin/:id',
    component: CustomerOptinDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerOptinPopupRoute: Routes = [
  {
    path: 'customer-optin/:id/edit',
    component: CustomerOptinPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-optin/:id/delete',
    component: CustomerOptinDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
