import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ContentComponent } from './content.component';
import { ContentDetailComponent } from './content-detail.component';
import { ContentPopupComponent, ContentDialogComponent } from './content-dialog.component';
import { ContentDeletePopupComponent } from './content-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ContentResolvePagingParams implements Resolve<any> {

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

export const contentRoute: Routes = [
  {
    path: 'content',
    component: ContentComponent,
    resolve: {
      'pagingParams': ContentResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.content.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'content-new',
    component: ContentDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.content.home.title'
    },
  }, {
    path: 'content/:id',
    component: ContentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.content.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contentPopupRoute: Routes = [
  {
    path: 'content/:id/edit',
    component: ContentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.content.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'content/:id/delete',
    component: ContentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.content.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
