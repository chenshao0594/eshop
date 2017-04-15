import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductReviewComponent } from './product-review.component';
import { ProductReviewDetailComponent } from './product-review-detail.component';
import { ProductReviewPopupComponent, ProductReviewDialogComponent } from './product-review-dialog.component';
import { ProductReviewDeletePopupComponent } from './product-review-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductReviewResolvePagingParams implements Resolve<any> {

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

export const productReviewRoute: Routes = [
  {
    path: 'product-review',
    component: ProductReviewComponent,
    resolve: {
      'pagingParams': ProductReviewResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReview.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-review-new',
    component: ProductReviewDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReview.home.title'
    },
  }, {
    path: 'product-review/:id',
    component: ProductReviewDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReview.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productReviewPopupRoute: Routes = [
  {
    path: 'product-review/:id/edit',
    component: ProductReviewPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReview.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-review/:id/delete',
    component: ProductReviewDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReview.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
