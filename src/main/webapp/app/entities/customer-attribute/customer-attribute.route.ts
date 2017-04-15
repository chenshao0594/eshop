import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerAttributeComponent } from './customer-attribute.component';
import { CustomerAttributeDetailComponent } from './customer-attribute-detail.component';
import { CustomerAttributePopupComponent, CustomerAttributeDialogComponent } from './customer-attribute-dialog.component';
import { CustomerAttributeDeletePopupComponent } from './customer-attribute-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerAttributeResolvePagingParams implements Resolve<any> {

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

export const customerAttributeRoute: Routes = [
  {
    path: 'customer-attribute',
    component: CustomerAttributeComponent,
    resolve: {
      'pagingParams': CustomerAttributeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-attribute-new',
    component: CustomerAttributeDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerAttribute.home.title'
    },
  }, {
    path: 'customer-attribute/:id',
    component: CustomerAttributeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerAttributePopupRoute: Routes = [
  {
    path: 'customer-attribute/:id/edit',
    component: CustomerAttributePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-attribute/:id/delete',
    component: CustomerAttributeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.customerAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
