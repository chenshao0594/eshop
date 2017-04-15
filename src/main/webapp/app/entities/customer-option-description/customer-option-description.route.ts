import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerOptionDescriptionComponent } from './customer-option-description.component';
import { CustomerOptionDescriptionDetailComponent } from './customer-option-description-detail.component';
import { CustomerOptionDescriptionPopupComponent, CustomerOptionDescriptionDialogComponent } from './customer-option-description-dialog.component';
import { CustomerOptionDescriptionDeletePopupComponent } from './customer-option-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerOptionDescriptionResolvePagingParams implements Resolve<any> {

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

export const customerOptionDescriptionRoute: Routes = [
  {
    path: 'customer-option-description',
    component: CustomerOptionDescriptionComponent,
    resolve: {
      'pagingParams': CustomerOptionDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-option-description-new',
    component: CustomerOptionDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionDescription.home.title'
    },
  }, {
    path: 'customer-option-description/:id',
    component: CustomerOptionDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerOptionDescriptionPopupRoute: Routes = [
  {
    path: 'customer-option-description/:id/edit',
    component: CustomerOptionDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-option-description/:id/delete',
    component: CustomerOptionDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
