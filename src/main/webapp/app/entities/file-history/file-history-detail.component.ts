import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { FileHistory } from './file-history.model';
import { FileHistoryService } from './file-history.service';

@Component({
    selector: 'jhi-file-history-detail',
    templateUrl: './file-history-detail.component.html'
})
export class FileHistoryDetailComponent implements OnInit, OnDestroy {

    fileHistory: FileHistory;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private fileHistoryService: FileHistoryService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['fileHistory']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFileHistories();
    }

    load(id) {
        this.fileHistoryService.find(id).subscribe((fileHistory) => {
            this.fileHistory = fileHistory;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.fileHistory.id !== undefined) {
            this.fileHistoryService.update(this.fileHistory)
                .subscribe((res: FileHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.fileHistoryService.create(this.fileHistory)
                .subscribe((res: FileHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: FileHistory) {
        this.eventManager.broadcast({ name: 'fileHistoryModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFileHistories() {
        this.eventSubscriber = this.eventManager.subscribe('fileHistoryListModification', (response) => this.load(this.fileHistory.id));
    }
}
