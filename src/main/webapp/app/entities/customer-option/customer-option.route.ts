import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerOptionComponent } from './customer-option.component';
import { CustomerOptionDetailComponent } from './customer-option-detail.component';
import { CustomerOptionPopupComponent, CustomerOptionDialogComponent } from './customer-option-dialog.component';
import { CustomerOptionDeletePopupComponent } from './customer-option-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerOptionResolvePagingParams implements Resolve<any> {

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

export const customerOptionRoute: Routes = [
  {
    path: 'customer-option',
    component: CustomerOptionComponent,
    resolve: {
      'pagingParams': CustomerOptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-option-new',
    component: CustomerOptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOption.home.title'
    },
  }, {
    path: 'customer-option/:id',
    component: CustomerOptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerOptionPopupRoute: Routes = [
  {
    path: 'customer-option/:id/edit',
    component: CustomerOptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOption.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-option/:id/delete',
    component: CustomerOptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOption.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
