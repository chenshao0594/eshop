import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductAvailabilityComponent } from './product-availability.component';
import { ProductAvailabilityDetailComponent } from './product-availability-detail.component';
import { ProductAvailabilityPopupComponent, ProductAvailabilityDialogComponent } from './product-availability-dialog.component';
import { ProductAvailabilityDeletePopupComponent } from './product-availability-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductAvailabilityResolvePagingParams implements Resolve<any> {

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

export const productAvailabilityRoute: Routes = [
  {
    path: 'product-availability',
    component: ProductAvailabilityComponent,
    resolve: {
      'pagingParams': ProductAvailabilityResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAvailability.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-availability-new',
    component: ProductAvailabilityDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAvailability.home.title'
    },
  }, {
    path: 'product-availability/:id',
    component: ProductAvailabilityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAvailability.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productAvailabilityPopupRoute: Routes = [
  {
    path: 'product-availability/:id/edit',
    component: ProductAvailabilityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAvailability.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-availability/:id/delete',
    component: ProductAvailabilityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAvailability.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
