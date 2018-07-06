/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AkcioniPlanDetailComponent } from 'app/entities/akcioni-plan/akcioni-plan-detail.component';
import { AkcioniPlan } from 'app/shared/model/akcioni-plan.model';

describe('Component Tests', () => {
    describe('AkcioniPlan Management Detail Component', () => {
        let comp: AkcioniPlanDetailComponent;
        let fixture: ComponentFixture<AkcioniPlanDetailComponent>;
        const route = ({ data: of({ akcioniPlan: new AkcioniPlan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AkcioniPlanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AkcioniPlanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AkcioniPlanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.akcioniPlan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
