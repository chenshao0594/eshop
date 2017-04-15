import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerOptionValueDescriptionComponent } from './customer-option-value-description.component';
import { CustomerOptionValueDescriptionDetailComponent } from './customer-option-value-description-detail.component';
import { CustomerOptionValueDescriptionPopupComponent, CustomerOptionValueDescriptionDialogComponent } from './customer-option-value-description-dialog.component';
import {
    CustomerOptionValueDescriptionDeletePopupComponent
} from './customer-option-value-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerOptionValueDescriptionResolvePagingParams implements Resolve<any> {

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

export const customerOptionValueDescriptionRoute: Routes = [
  {
    path: 'customer-option-value-description',
    component: CustomerOptionValueDescriptionComponent,
    resolve: {
      'pagingParams': CustomerOptionValueDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-option-value-description-new',
    component: CustomerOptionValueDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValueDescription.home.title'
    },
  }, {
    path: 'customer-option-value-description/:id',
    component: CustomerOptionValueDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerOptionValueDescriptionPopupRoute: Routes = [
  {
    path: 'customer-option-value-description/:id/edit',
    component: CustomerOptionValueDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-option-value-description/:id/delete',
    component: CustomerOptionValueDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
