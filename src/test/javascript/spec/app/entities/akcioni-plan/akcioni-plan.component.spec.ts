/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AkcioniPlanComponent } from 'app/entities/akcioni-plan/akcioni-plan.component';
import { AkcioniPlanService } from 'app/entities/akcioni-plan/akcioni-plan.service';
import { AkcioniPlan } from 'app/shared/model/akcioni-plan.model';

describe('Component Tests', () => {
    describe('AkcioniPlan Management Component', () => {
        let comp: AkcioniPlanComponent;
        let fixture: ComponentFixture<AkcioniPlanComponent>;
        let service: AkcioniPlanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AkcioniPlanComponent],
                providers: []
            })
                .overrideTemplate(AkcioniPlanComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AkcioniPlanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AkcioniPlanService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AkcioniPlan(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.akcioniPlans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
