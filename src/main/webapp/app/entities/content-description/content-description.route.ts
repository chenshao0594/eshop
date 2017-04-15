import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ContentDescriptionComponent } from './content-description.component';
import { ContentDescriptionDetailComponent } from './content-description-detail.component';
import { ContentDescriptionPopupComponent, ContentDescriptionDialogComponent } from './content-description-dialog.component';
import { ContentDescriptionDeletePopupComponent } from './content-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ContentDescriptionResolvePagingParams implements Resolve<any> {

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

export const contentDescriptionRoute: Routes = [
  {
    path: 'content-description',
    component: ContentDescriptionComponent,
    resolve: {
      'pagingParams': ContentDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.contentDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'content-description-new',
    component: ContentDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.contentDescription.home.title'
    },
  }, {
    path: 'content-description/:id',
    component: ContentDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.contentDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contentDescriptionPopupRoute: Routes = [
  {
    path: 'content-description/:id/edit',
    component: ContentDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.contentDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'content-description/:id/delete',
    component: ContentDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.contentDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
