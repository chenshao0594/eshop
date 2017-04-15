import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerOptionValueComponent } from './customer-option-value.component';
import { CustomerOptionValueDetailComponent } from './customer-option-value-detail.component';
import { CustomerOptionValuePopupComponent, CustomerOptionValueDialogComponent } from './customer-option-value-dialog.component';
import { CustomerOptionValueDeletePopupComponent } from './customer-option-value-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerOptionValueResolvePagingParams implements Resolve<any> {

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

export const customerOptionValueRoute: Routes = [
  {
    path: 'customer-option-value',
    component: CustomerOptionValueComponent,
    resolve: {
      'pagingParams': CustomerOptionValueResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-option-value-new',
    component: CustomerOptionValueDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValue.home.title'
    },
  }, {
    path: 'customer-option-value/:id',
    component: CustomerOptionValueDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerOptionValuePopupRoute: Routes = [
  {
    path: 'customer-option-value/:id/edit',
    component: CustomerOptionValuePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-option-value/:id/delete',
    component: CustomerOptionValueDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
