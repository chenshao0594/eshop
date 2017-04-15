import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerOptionSetComponent } from './customer-option-set.component';
import { CustomerOptionSetDetailComponent } from './customer-option-set-detail.component';
import { CustomerOptionSetPopupComponent, CustomerOptionSetDialogComponent } from './customer-option-set-dialog.component';
import { CustomerOptionSetDeletePopupComponent } from './customer-option-set-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerOptionSetResolvePagingParams implements Resolve<any> {

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

export const customerOptionSetRoute: Routes = [
  {
    path: 'customer-option-set',
    component: CustomerOptionSetComponent,
    resolve: {
      'pagingParams': CustomerOptionSetResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-option-set-new',
    component: CustomerOptionSetDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionSet.home.title'
    },
  }, {
    path: 'customer-option-set/:id',
    component: CustomerOptionSetDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerOptionSetPopupRoute: Routes = [
  {
    path: 'customer-option-set/:id/edit',
    component: CustomerOptionSetPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-option-set/:id/delete',
    component: CustomerOptionSetDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
